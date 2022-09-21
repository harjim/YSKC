using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_audit_resultAndProposalTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_audit_proposal",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    moduleId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    proposalId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_proposal", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_audit_result",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    moduleId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    documentId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_result", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_audit_proposal_proposalId",
                table: "p_audit_proposal",
                column: "proposalId",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_p_audit_result_documentId",
                table: "p_audit_result",
                column: "documentId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_audit_proposal");

            migrationBuilder.DropTable(
                name: "p_audit_result");
        }
    }
}
