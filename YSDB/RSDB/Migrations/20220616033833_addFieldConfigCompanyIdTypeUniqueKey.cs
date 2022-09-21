using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addFieldConfigCompanyIdTypeUniqueKey : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_fieldConfig_companyId_type",
                table: "fieldConfig",
                columns: new[] { "companyId", "type" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_fieldConfig_companyId_type",
                table: "fieldConfig");
        }
    }
}
