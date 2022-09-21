using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_docFile_footerCompanyIdYearProjectIdUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_docFile_footer_companyId_year_projectId",
                table: "p_docFile_footer",
                columns: new[] { "companyId", "year", "projectId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_docFile_footer_companyId_year_projectId",
                table: "p_docFile_footer");
        }
    }
}
