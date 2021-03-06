
/* Copyright (C) 2009-2014 Syed Asad Rahman <asad.rahman@bioinceptionlabs.com>
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.smsd.tools;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import static org.junit.Assert.*;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * test-smsd
 *
 * @author Syed Asad Rahman <asad.rahman@bioinceptionlabs.com>
 */
public class MoleculeSanityCheckTest {

    public MoleculeSanityCheckTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkAndCleanMolecule method, of class MoleculeSanityCheck.
     *
     * @throws InvalidSmilesException
     */
    @Test
    public void testCheckAndCleanMolecule() throws InvalidSmilesException {
        //////System.out.println("checkAndCleanMolecule");
        String fragmentMolSmiles = "C1=CC=CC=C1.C1=CC2=C(C=C1)C=CC=C2";
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer molecule = sp.parseSmiles(fragmentMolSmiles);
        IAtomContainer expResult = sp.parseSmiles("C1=CC2=C(C=C1)C=CC=C2");
        IAtomContainer result = ExtAtomContainerManipulator.checkAndCleanMolecule(molecule);
        assertEquals(expResult.getBondCount(), result.getBondCount());
    }

    /**
     * Test of fixAromaticity method, of class MoleculeSanityCheck.
     *
     * @throws InvalidSmilesException
     */
    @Test
    public void testFixAromaticity() throws InvalidSmilesException {
        //////System.out.println("fixAromaticity");
        String rawMolSmiles = "C1=CC2=C(C=C1)C=CC=C2";
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer mol = sp.parseSmiles(rawMolSmiles);
        ExtAtomContainerManipulator.checkAndCleanMolecule(mol);
        int count = 0;
        for (IBond b : mol.bonds()) {
            if (b.getFlag(CDKConstants.ISAROMATIC) && b.getOrder().equals(IBond.Order.DOUBLE)) {
                count++;
            }
        }
        assertEquals(5, count);
    }
}
